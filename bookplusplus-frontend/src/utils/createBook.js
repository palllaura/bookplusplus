export function createBook({
                               id,
                               bookWidthInMm = 50,
                               bookHeightInMm = 120,
                               color = '#D9722C',
                               fontsize = 16,
                               xposition = 100,
                               yposition = 200,
                               title = 'Untitled'
                           }, mm) {
    return {
        id,
        width: bookWidthInMm * mm,
        height: bookHeightInMm * mm,
        color,
        fontsize: fontsize * mm,
        x: xposition * mm,
        y: yposition * mm,
        title
    };
}