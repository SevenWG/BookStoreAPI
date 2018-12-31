package com.team404.bookstore.entity;

public class UserAddressCombine {
    private UserEntity userEntity;
    private AddressEntity addressEntity;

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }

    public UserAddressCombine() {
    }

    public UserAddressCombine(UserEntity userEntity, AddressEntity addressEntity) {
        this.userEntity = userEntity;
        this.addressEntity = addressEntity;
    }
}
