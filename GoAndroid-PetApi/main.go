package main

import (
	"GoAndroid-PetApi/pkg/common/config"
	"GoAndroid-PetApi/pkg/common/db"
	"GoAndroid-PetApi/pkg/pets"
	"log"

	"github.com/gofiber/fiber/v2"
)

func main() {
	config, err := config.LoadConfig()

	if err != nil {
		log.Fatalln("Could not load config")
	}

	db, err := db.InitDb(&config)

	if err != nil {
		log.Fatalln("Could not load the database")
	}

	err = pets.MigratePets(db)

	if err != nil {
		log.Fatalln("Could not migrate pet database")
	}

	r := pets.Repository{
		DB: db,
	}

	app := fiber.New()
	r.SetupRoutes(app)

	app.Listen(config.Port)
}
