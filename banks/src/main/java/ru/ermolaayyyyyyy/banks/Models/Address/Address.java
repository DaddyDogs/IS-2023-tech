package ru.ermolaayyyyyyy.banks.Models.Address;

import lombok.Getter;
import ru.ermolaayyyyyyy.banks.Exceptions.AddressException;

@Getter
public class Address {
    private Address(String streetName, String buildingNumber, String entranceNumber, int floorNumber, int flatNumber) {
        this.streetName = streetName;
        this.buildingNumber = buildingNumber;
        this.entranceNumber = entranceNumber;
        this.floorNumber = floorNumber;
        this.flatNumber = flatNumber;
    }

    public static StreetBuilder getBuilder() {
        return new AddressBuilderImpl();
    }

    public String streetName;
    public String buildingNumber;
    public String entranceNumber;
    public int floorNumber;
    public int flatNumber;
    private static class AddressBuilderImpl implements StreetBuilder, BuildingBuilder, AddressBuilder, FlatBuilder
    {
        private String streetName;
        private String buildingNumber;
        private String entranceNumber;
        private int floorNumber;
        private int flatNumber;

        public BuildingBuilder withStreet(String streetName) {
            Validate(streetName);

            this.streetName = streetName;
            return this;
        }

        public FlatBuilder withBuilding(String buildingNumber) {
            Validate(buildingNumber);

            this.buildingNumber = buildingNumber;
            return this;
        }

        public AddressBuilder withEntrance(String entranceNumber) {
            Validate(entranceNumber);

            this.entranceNumber = entranceNumber;
            return this;
        }

        public AddressBuilder withFloor(int floorNumber) {
            if (floorNumber < 1) {
                throw AddressException.invalidNumberException("Floor", floorNumber);
            }

            this.floorNumber = floorNumber;
            return this;
        }

        public AddressBuilder withFlat(int flatNumber) {
            if (flatNumber < 1) {
                throw AddressException.invalidNumberException("Flat", flatNumber);
            }

            this.flatNumber = flatNumber;
            return this;
        }

        public Address build() {
            Validate(streetName);
            Validate(buildingNumber);

            return new Address(streetName, buildingNumber, entranceNumber, floorNumber, flatNumber);
        }

        private static void Validate(String input) {
            if (input == null || input.isEmpty())
            {
                throw new IllegalArgumentException(input);
            }
        }
    }
}
