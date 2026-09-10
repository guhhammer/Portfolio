function prob = random_walk_theoretical(n, k)
%RANDOM_WALK_THEORETICAL Exact P(position = k after n steps).
%   Finishing at k requires (n + k) / 2 steps to the right, so the
%   probability is C(n, (n + k) / 2) / 2^n, and 0 when the parity of n
%   and k differs.

    if mod(n, 2) == mod(k, 2)
        prob = nchoosek(n, (n + k) / 2) / (2 ^ n);
    else
        prob = 0;
    end
end
