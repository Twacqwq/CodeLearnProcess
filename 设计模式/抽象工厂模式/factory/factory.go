package factory

import "fmt"

// 抽象层
type AbstractApple interface {
	ShowApple()
}

type AbstractBanana interface {
	ShowBanana()
}

// 抽象工厂
type AbstractFactory interface {
	CreateApple() AbstractApple
	CreateBanana() AbstractBanana
}

type ChinaApple struct{}

func (apple *ChinaApple) ShowApple() {
	fmt.Println("show ChinaApple")
}

type ChinaBanana struct{}

func (bana *ChinaBanana) ShowBanana() {
	fmt.Println("show ChinaBanana")
}

type ChinaFactory struct{}

func (cf *ChinaFactory) CreateApple() AbstractApple {
	return new(ChinaApple)
}

func (cf *ChinaFactory) CreateBanana() AbstractBanana {
	return new(ChinaBanana)
}
