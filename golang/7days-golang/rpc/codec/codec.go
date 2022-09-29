package codec

import "io"

type Header struct {
	Seq           uint64 // 请求标识
	ServiceMethod string // format: "Airth.Sum"
	Error         string
}

// 消息体编解码器
type Codec interface {
	io.Closer
	ReadHeader(*Header) error
	ReadBody(interface{}) error
	Write(*Header, interface{}) error
}

type NewCodecFunc func(io.ReadWriteCloser) Codec

type Type string

const (
	JsonType = "application/json"
	GobType  = "application/gob"
)

var NewCodecFuncMap map[Type]NewCodecFunc

func init() {
	NewCodecFuncMap = make(map[Type]NewCodecFunc)
	NewCodecFuncMap[GobType] = NewGobCodec
}
