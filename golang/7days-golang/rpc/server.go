package rpc

import (
	"encoding/json"
	"fmt"
	"geerpc/codec"
	"io"
	"log"
	"net"
	"reflect"
	"sync"
)

const MagicNumber = 0x1338ed

type Option struct {
	MagicNumber int
	CodecType   codec.Type
}

var DefaultOption = &Option{
	MagicNumber: MagicNumber,
	CodecType:   codec.GobType,
}

type Server struct{}

func NewServer() *Server {
	return &Server{}
}

var DefaultServer = NewServer()

func (server *Server) ServeConn(conn io.ReadWriteCloser) {
	defer conn.Close()
	var opt Option
	if err := json.NewDecoder(conn).Decode(&opt); err != nil {
		log.Println("rpc server: option error,", err)
		return
	}
	if opt.MagicNumber != MagicNumber {
		log.Printf("rpc server: magic number error %x", MagicNumber)
		return
	}
	f := codec.NewCodecFuncMap[opt.CodecType]
	if f == nil {
		log.Println("rpc server: codecType error,", opt.CodecType)
		return
	}
	server.serveCodec(f(conn))
}

// 失败请求占位符
var invalidRequest = struct{}{}

// 并发处理消息
func (server *Server) serveCodec(cc codec.Codec) {
	sending := new(sync.Mutex)
	wg := new(sync.WaitGroup)
	//readRequest (header, body)
	for {
		req, err := server.readRequest(cc)
		if err != nil {
			if req == nil {
				break
			}
			req.h.Error = err.Error()
			server.sendResponse(cc, req.h, invalidRequest, sending)
			continue
		}
		wg.Add(1)
		go server.handleRequest(cc, req, wg, sending)
	}
	wg.Wait()
	_ = cc.Close()
}

type request struct {
	h      *codec.Header
	argv   reflect.Value
	replyv reflect.Value
}

func (server *Server) readRequestHeader(cc codec.Codec) (*codec.Header, error) {
	var h *codec.Header
	if err := cc.ReadHeader(h); err != nil {
		if err != io.EOF && err != io.ErrUnexpectedEOF {
			log.Println("rpc server: read header error")
		}
		return nil, err
	}
	return h, nil
}

func (server *Server) readRequest(cc codec.Codec) (*request, error) {
	h, err := server.readRequestHeader(cc)
	if err != nil {
		return nil, err
	}
	req := &request{h: h}
	req.argv = reflect.New(reflect.TypeOf(""))
	if err := cc.ReadBody(req.argv.Interface()); err != nil {
		log.Println("rpc server: read req argv err", err)
	}
	return req, nil
}

func (server *Server) sendResponse(cc codec.Codec, h *codec.Header, body interface{}, sending *sync.Mutex) {
	sending.Lock()
	defer sending.Unlock()
	if err := cc.Write(h, body); err != nil {
		log.Println("rpc server: write response error:", err)
		return
	}
}

// 处理请求(每一条消息)
func (server *Server) handleRequest(cc codec.Codec, req *request, wg *sync.WaitGroup, sending *sync.Mutex) {
	defer wg.Done()
	//打印请求头和请求信息
	log.Println(req.h, req.argv.Elem())
	req.replyv = reflect.ValueOf(fmt.Sprintf("geerpc resp %d", req.h.Seq))
	server.sendResponse(cc, req.h, req.replyv.Interface(), sending)
}

func (server *Server) Accept(lis net.Listener) {
	for {
		conn, err := lis.Accept()
		if err != nil {
			log.Println("rpc server: accept error:", err)
			return
		}
		go server.ServeConn(conn)
	}
}

func Accept(lis net.Listener) {
	DefaultServer.Accept(lis)
}