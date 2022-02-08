function [ ] = pdfnorm(mu, sigma, nElm)

e=normrnd(mu,sigma, 1,nElm);
step = (max(e)-min(e))^2/1000;
r=min(e):step:max(e);
[y1, x] = hist(e,r);
y1 = y1/nElm*(1/step);    %multiplica por 10 porque foi 
                    %distribuído no histograma a cada 0.1
y2=normpdf(x,mu,sigma);
plot(x, y1, '-blue', x, y2, '-red');
end