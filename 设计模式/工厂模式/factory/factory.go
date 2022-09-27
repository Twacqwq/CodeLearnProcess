package factory

import "fmt"

type Fruit interface {
	Show()
}

type AbstractFactory interface {
	CreateFruit() Fruit
}

type Apple struct{}

func (apple *Apple) Show() {
	fmt.Println("I am Apple")
}

type Banana struct{}

func (banana *Banana) Show() {
	fmt.Println("I am Banana")
}

type AppleFactory struct {
	AbstractFactory
}

type BananaFactory struct {
	AbstractFactory
}

func (af *AppleFactory) CreateFruit() Fruit {
	return new(Apple)
}

func (bf *BananaFactory) CreateFruit() Fruit {
	return new(Banana)
}
