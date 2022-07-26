package pets

import (
	"GoAndroid-PetApi/pkg/common/models"
	"net/http"

	"github.com/gofiber/fiber/v2"
)

func (r *Repository) UpdatePet(context *fiber.Ctx) error {

	id := context.Params("id")
	if id == "" {
		context.Status(http.StatusInternalServerError).JSON(
			&fiber.Map{"message": "id cannot be empty"},
		)
		return nil
	}

	pet := PetRequestBody{}

	err := context.BodyParser(&pet)
	if err != nil {
		context.Status(http.StatusUnprocessableEntity).JSON(
			&fiber.Map{"message": "request failed"},
		)
		return err
	}

	petFromDB := &models.Pet{}
	err = r.DB.Where("id = ?", id).First(petFromDB).Error

	if err != nil {
		context.Status(http.StatusBadRequest).JSON(
			&fiber.Map{"message": "could not find the pet to update"},
		)
		return err
	}

	petFromDB.Name = pet.Name
	petFromDB.Age = pet.Age
	petFromDB.Species = pet.Species
	petFromDB.Food = pet.Food
	petFromDB.Description = pet.Description
	petFromDB.Photo = pet.Photo

	err = r.DB.Save(&petFromDB).Error

	if err != nil {
		context.Status(http.StatusBadRequest).JSON(
			&fiber.Map{"message": "could not save changes"},
		)
	}

	context.Status(http.StatusOK).JSON(
		&fiber.Map{"message": "pet has been updated"},
	)

	return nil
}
