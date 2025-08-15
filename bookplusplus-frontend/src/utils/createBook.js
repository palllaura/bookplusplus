export function createBook({
                               id,
                               bookWidthInMm = 50,
                               bookHeightInMm = 120,
                               color = '#D9722C',
                               xposition = 100,
                               yposition = 200,
                               title = 'Untitled'
}, mm) {
    return {id,
        width: bookWidthInMm * mm,
        height: bookHeightInMm * mm,
        color,
        x: xposition * mm,
        y: yposition * mm,
        title
    };
}