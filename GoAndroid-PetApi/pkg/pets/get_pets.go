package pets

import (
	"GoAndroid-PetApi/pkg/common/models"
	"net/http"

	"github.com/gofiber/fiber/v2"
)

func (r *Repository) GetPets(context *fiber.Ctx) error {
	petModel := &[]models.Pet{}

	err := r.DB.Find(petModel).Error
	if err != nil {
		context.Status(http.StatusBadRequest).JSON(
			&fiber.Map{"message": "could not get pets"},
		)
		return err
	}

	context.Status(http.StatusOK).JSON(
		&fiber.Map{
			"message": "pets fetched successfully",
			"data":    petModel,
		},
	)

	return nil
}
