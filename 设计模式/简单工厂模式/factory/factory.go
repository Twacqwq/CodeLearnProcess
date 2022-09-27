package factory

import "fmt"

type Abstract interface {
	Event()
}

type Basic1 struct {
}

func (b *Basic1) Event() {
	fmt.Println("Basic1.Event")
}

type Basic2 struct {
}

func (b *Basic2) Event() {
	fmt.Println("Basic2.Event")
}

type Factory struct{}

func (fac *Factory) NewFactory(kind string) Abstract {
	var ab Abstract

	if kind == "basic1" {
		ab = new(Basic1)
	} else if kind == "basic2" {
		ab = new(Basic2)
	}

	return ab
}
