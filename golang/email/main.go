package main

import (
	"log"
	"net/smtp"
	"net/textproto"

	"github.com/jordan-wright/email"
)

func main() {
	e := &email.Email{
		To: []string{"1298489481@qq.com"},
		From: "1298489481@qq.com",
		Subject: "golang测试邮件",
		Text: []byte("Golang Study"),
		HTML: []byte("<h1>Hello</h1>"),
		Headers: textproto.MIMEHeader{},
	}
	if err := e.Send("smtp.qq.com:25", smtp.PlainAuth("", e.From, "supyynrcxkbjbacd", "smtp.qq.com")); err != nil {
		log.Fatal(err)
	}
	log.Println("send successfully ...")
}