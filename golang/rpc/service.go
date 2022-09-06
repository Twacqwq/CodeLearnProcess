package service

import "context"

type Args struct {
    A int
    B int
}

type Reply struct {
    C int
}

//Service的类型并不重要。你可以使用自定义类型来保持状态，或者直接使用 struct{}、 int。
//这是暴露给外部的Service变量
type Arith int

func (t *Arith) Mul(ctx context.Context, args *Args, reply *Reply) error {
    reply.C = args.A * args.B
    return nil
}