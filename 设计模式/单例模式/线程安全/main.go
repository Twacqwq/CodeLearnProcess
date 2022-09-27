package main

import "sync"

// 私有化
type singleton struct{}

var instance *singleton
var once sync.Once

func GetInstance() *singleton {
	once.Do(func() {
		instance = new(singleton)
	})
	return instance
}

func (s *singleton) Do() {
	println("do something...")
}

func main() {
	s := GetInstance()
	s.Do()
}
