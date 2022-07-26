package models

type Pet struct {
	Id          uint   `gorm:"primary key;autoIncrement" json:"id"`
	Name        string `json:"name"`
	Age         int    `json:"age"`
	Species     string `json:"species"`
	Food        string `json:"food"`
	Description string `json:"description"`
	Photo       string `json:"photo"`
}
