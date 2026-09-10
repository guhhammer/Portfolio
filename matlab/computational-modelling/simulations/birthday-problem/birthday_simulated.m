function prob = birthday_simulated(k, n)
%BIRTHDAY_SIMULATED Monte Carlo estimate of the birthday-problem probability.
%   prob = BIRTHDAY_SIMULATED(k, n) draws k random birthdays n times and
%   returns the fraction of draws in which at least two birthdays coincide.
%   Uses only core functions (randi, unique).

    all_different = zeros(n, 1);
    for draw = 1:n
        birthdays = randi(365, 1, k);
        if numel(unique(birthdays)) == numel(birthdays)
            all_different(draw) = 1;
        end
    end

    prob = 1 - sum(all_different) / n;
end
