package main

// 私有化
type singleton struct{}

var instance *singleton = new(singleton)

func GetInstance() *singleton {
	return instance
}

func (s *singleton) Do() {
	println("do something...")
}

func main() {
	s := GetInstance()
	s.Do()
}
