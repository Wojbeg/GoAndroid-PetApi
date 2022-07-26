package pets

import (
	"GoAndroid-PetApi/pkg/common/models"
	"net/http"

	"github.com/gofiber/fiber/v2"
)

func (r *Repository) CreatePet(context *fiber.Ctx) error {
	pet := PetRequestBody{}

	err := context.BodyParser(&pet)
	if err != nil {
		context.Status(http.StatusUnprocessableEntity).JSON(
			&fiber.Map{"message": "request failed"},
		)
		return err
	}

	petFromDB := models.Pet{}
	petFromDB.Name = pet.Name
	petFromDB.Age = pet.Age
	petFromDB.Species = pet.Species
	petFromDB.Food = pet.Food
	petFromDB.Description = pet.Description
	petFromDB.Photo = pet.Photo

	err = r.DB.Create(&petFromDB).Error
	if err != nil {
		context.Status(http.StatusBadRequest).JSON(
			&fiber.Map{"message": "could not create the pet"},
		)
		return err
	}

	context.Status(http.StatusOK).JSON(
		&fiber.Map{"message": "pet has been added"},
	)

	return nil
}
