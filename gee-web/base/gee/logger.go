package gee

import (
	"log"
	"time"
)

func Logger() HandlerFunc {
	return func(ctx *Context) {
		t := time.Now()
		ctx.Next() //执行handler
		log.Printf("[%d] %s in %v", ctx.StatusCode, ctx.Req.RequestURI, time.Since(t))
	}
}
