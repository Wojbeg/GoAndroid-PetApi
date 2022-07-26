package pets

import (
	"GoAndroid-PetApi/pkg/common/models"
	"net/http"

	"github.com/gofiber/fiber/v2"
)

func (r *Repository) GetPetById(context *fiber.Ctx) error {

	id := context.Params("id")
	if id == "" {
		context.Status(http.StatusInternalServerError).JSON(
			&fiber.Map{"message": "id cannot be empty"},
		)
		return nil
	}

	petModel := &models.Pet{}

	err := r.DB.Where("id = ?", id).First(petModel).Error

	if err != nil {
		context.Status(http.StatusBadRequest).JSON(
			&fiber.Map{"message": "could not get the pet"},
		)
		return err
	}

	context.Status(http.StatusOK).JSON(
		&fiber.Map{
			"pet":     petModel,
			"message": "pet by id fetched successfully",
		},
	)

	return nil
}
