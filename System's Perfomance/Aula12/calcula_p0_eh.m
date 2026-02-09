function [p0, eh] = calcula_p0_eh (ro, m)
    p0 = 1+((m*ro)^m)/(factorial(m)*(1-ro));
    for n=1:m-1
        p0 = p0 + (((m*ro)^n)/factorial(n));
    end
    p0 = 1 / p0;
    eh = ((m*ro)^m)/((1-ro)*factorial(m))*p0;
end