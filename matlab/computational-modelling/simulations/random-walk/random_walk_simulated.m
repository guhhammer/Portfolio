function prob = random_walk_simulated(n, k, n_sim)
%RANDOM_WALK_SIMULATED Monte Carlo estimate of P(position = k after n steps).
%   Runs n_sim walks of n steps (each step -1 or +1 with probability 1/2)
%   and returns the fraction that finish exactly at k.  When n and k have
%   different parity the position k is unreachable, so the answer is 0.

    hits = 0;

    if mod(n, 2) == mod(k, 2)
        for i = 1:n_sim
            position = 0;
            for j = 1:n
                if randi(2) == 1
                    position = position - 1;
                else
                    position = position + 1;
                end
            end
            if position == k
                hits = hits + 1;
            end
        end
        prob = hits / n_sim;
    else
        prob = 0;
    end
end
