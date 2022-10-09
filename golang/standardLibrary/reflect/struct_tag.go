package main

import (
	"fmt"
	"io"
	"reflect"
)

type User struct {
	Email  string `mcl:"email"`
	Name   string `mcl:"name"`
	Age    int    `mcl:"age"`
	Github string `mcl:"github.com" default:"twac"`
}

var _ io.Closer = (*User)(nil)

func (u *User) Close() error {
	return nil
}

func main() {
	var u interface{} = User{}
	typ := reflect.TypeOf(u)
	println(typ.Kind()) //reflect.Struct itoa = 25
	if typ.Kind() != reflect.Struct {
		return
	}
	for i := 0; i < typ.NumField(); i++ {
		f := typ.Field(i)
		fmt.Println(f.Tag.Get("mcl"), f.Tag.Get("default"))
	}
}
