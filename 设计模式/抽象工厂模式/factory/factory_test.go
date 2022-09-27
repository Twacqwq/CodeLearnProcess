package factory

import "testing"

func TestFactory(t *testing.T) {
	chinaFac := new(ChinaFactory)

	apple := chinaFac.CreateApple()
	apple.ShowApple()

	banana := chinaFac.CreateBanana()
	banana.ShowBanana()
}
