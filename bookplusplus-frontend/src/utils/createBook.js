export function createBook({
                               id,
                               title = 'Untitled',
                               width = 50,
                               height = 120,
                               color = '#D9722C',
                               fontcolor = '#ffffff',
                               fontsize = 16,
                               xposition = 100,
                               yposition = 200,
                           }, mm) {
    return {
        id,
        title,
        width: width * mm,
        height: height * mm,
        color,
        fontcolor,
        fontsize: fontsize * mm,
        x: xposition * mm,
        y: yposition * mm,
    };
}