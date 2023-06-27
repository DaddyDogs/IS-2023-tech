package ru.ermolaayyyyyyy.banks.Models.Address;

public interface AddressBuilder {
    AddressBuilder withEntrance(String entranceNumber);
    AddressBuilder withFloor(int floorNumber);
    Address build();
}
