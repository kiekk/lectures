package com.tobyspring.studytobyspring.policy;

import com.tobyspring.studytobyspring.domain.User;

public interface UserLevelUpgradePolicy {
    boolean canUpgradeLevel(User user);

    void upgradeLevel(User user);
}
