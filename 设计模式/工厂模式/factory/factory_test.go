package factory

import "testing"

func TestFactory(t *testing.T) {
	appfac := new(AppleFactory)
	apple := appfac.CreateFruit()
	apple.Show()

	banaFac := new(BananaFactory)
	banana := banaFac.CreateFruit()
	banana.Show()
}
