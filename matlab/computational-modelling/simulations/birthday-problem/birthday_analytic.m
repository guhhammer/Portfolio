function prob = birthday_analytic(k)
%BIRTHDAY_ANALYTIC Exact probability that at least two of k people share a birthday.
%   Computes the complementary event (all k birthdays different) as the
%   product 365/365 * 364/365 * ... * (365-k+1)/365 and subtracts it from 1.

    all_different = 1;
    for i = (365 - k + 1):365
        all_different = all_different * (i / 365);
    end

    prob = 1 - all_different;
end
