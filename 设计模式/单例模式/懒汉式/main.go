package main

// 私有化
type singleton struct{}

var instance *singleton

// 非线程安全
func GetInstance() *singleton {
	if instance == nil {
		instance = new(singleton)
	}
	return instance
}

func (s *singleton) Do() {
	println("do something...")
}

func main() {
	s := GetInstance()
	s.Do()
}
