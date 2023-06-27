package ru.ermolaayyyyyyy.banks.Entities.Client;

import ru.ermolaayyyyyyy.banks.Models.Address.Address;
import ru.ermolaayyyyyyy.banks.Models.Passport;

public interface ClientBuilder {
    ClientBuilder withAddress(Address address);
    ClientBuilder withPassport(Passport passport);
    Client build();
}
