% Uses Kinderman and Monahan method. Reference: Kinderman,
% A.J. and Monahan, J.F., "Computer generation of random
% variables using the ratio of uniform deviates", ACM Trans
% Math Software, 3, (1977), pp257-260.
function [ out ] = rndnorm(mu, sigma, n, m)
NV_MAGICCONST = 4 * exp(-0.5)/ sqrt(2.0);
out = zeros(n, m);
for k = 1:m
    for j = 1:n
        while (true) 
            u1 = rand();
            u2 = 1.0 - rand();
            z = NV_MAGICCONST*(u1-0.5)/u2;
            zz = z*z/4.0;
            if zz <= -log(u2)
                break
            end     
        end
    out(j,k) = mu + z*sigma;
    end
end
end
