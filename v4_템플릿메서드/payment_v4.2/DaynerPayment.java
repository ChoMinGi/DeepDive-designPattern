package net.dayner.api.domain.payment;

import net.dayner.api.domain.user.entity.User;

public interface DaynerPayment {

    void use(int newBalance);
    void reset();
    void registration(User user);
    void updateImage(String newImageUrl);
}
