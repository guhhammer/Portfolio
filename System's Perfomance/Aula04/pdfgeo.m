function [ ] = pdfgeo(p, nElm)
    aux = rndgeo(p,1,nElm);
    x = 0:max(aux);
    [y1 x] = hist(aux, x);
    y1 = y1/nElm;
    y2 = geopdf(x,p);
    plot(x,y1,'-blue', x,y2,'-red')
end