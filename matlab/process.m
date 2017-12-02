go=true;
while go
    cam = webcam ;
    img=snapshot(cam)
    R = img(:, :, 1);
    G = img(:, :, 2);
    B = img(:, :, 3);
    Gray = 0.229*R + 0.587*G + 0.114*B;
    imshow(Gray);
    BW=imbinarize(Gray);
    se = strel('line',5,5);
    BW2 = imdilate(BW,se);
    erodedBW = imerode(BW2,se);
    s = regionprops(erodedBW,  {'Centroid','Area','Perimeter'});
    numObj = numel(s);
    str="";
    for k = 1 : numObj
        temp=s(k).Perimeter*s(k).Perimeter;
        if 4*3.14*s(k).Area/temp>0.9
            if s(k).Centroid(2)<25
                if s(k).Centroid(1)<100
                    str=str+"1"+" "
                else
                    str=str+"2"+" "
                end
            elseif s(k).Centroid(2)<55
                if s(k).Centroid(1)<100
                    str=str+"3"+" "
                else
                    str=str+"4"+" "
                end
            elseif s(k).Centroid(2)<85
                if s(k).Centroid(1)<100
                    str=str+"5"+" "
                else
                    str=str+"6"+" "
                end
            else
                if s(k).Centroid(1)<100
                    str=str+"7"+" "
                else
                    str=str+"8"+" "
                end
            end
        end
    end
   fid = fopen('ny.txt','wt');
   fprintf(fid, '%s', str);
   fclose(fid);
end