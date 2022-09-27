package factory

import "testing"

func TestFactory(t *testing.T) {
	factory := new(Factory)

	basic1 := factory.NewFactory("basic1")
	basic1.Event()

	basic2 := factory.NewFactory("basic2")
	basic2.Event()
}
