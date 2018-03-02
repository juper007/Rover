package com.rover.juper007;

public class User {
    public int id;
    public String name;
    public String image;
    public String phone;
    public String email;

    public User(int userId, String userName, String userImage, String userPhone, String userEmail) {
        this.id = userId;
        this.name = userName;
        this.image = userImage;
        this.phone = userPhone;
        this.email = userEmail;
    }

    public String toString() {
        return String.format("ID    : %d \n" +
                "Name  : %s\n" +
                "Image : %s\n" +
                "Phone : %s\n" +
                "Email : %s", id, name, image, phone, email);
    }
}
