function prob = dice_probability(n)
%DICE_PROBABILITY Simulate "the only six in four throws is the fourth one".
%   prob = DICE_PROBABILITY(n) throws a fair die four times, n times over,
%   and returns the fraction of experiments in which exactly one six came
%   up and it was on the fourth throw.  The exact answer is
%   (5/6)^3 * (1/6) = 0.0965.
%   Uses only core functions (randi).

    hits = 0;
    for i = 1:n
        throws = randi(6, 1, 4);
        sixes = find(throws == 6);
        if numel(sixes) == 1 && sixes == 4
            hits = hits + 1;
        end
    end
    prob = hits / n;
end
