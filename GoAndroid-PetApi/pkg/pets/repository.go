package pets

import (
	"GoAndroid-PetApi/pkg/common/models"

	"github.com/gofiber/fiber/v2"
	"gorm.io/gorm"
)

type PetRequestBody struct {
	Name        string `json:"name"`
	Age         int    `json:"age"`
	Species     string `json:"species"`
	Food        string `json:"food"`
	Description string `json:"description"`
	Photo       string `json:"photo"`
}

type Repository struct {
	DB *gorm.DB
}

func (r *Repository) SetupRoutes(app *fiber.App) {
	api := app.Group("/api")
	api.Post("/create_pet", r.CreatePet)
	api.Delete("/delete_pet/:id", r.DeletePet)
	api.Get("/pets/:id", r.GetPetById)
	api.Get("/pets", r.GetPets)
	api.Put("/update_pet/:id", r.UpdatePet)
}

func MigratePets(db *gorm.DB) error {
	err := db.AutoMigrate(&models.Pet{})
	return err
}
