export function createBook({id, bookWidthInMm = 50, bookHeightInMm = 120, color = '#D9722C',
                        xposition = 100, yposition = 200, title = 'Untitled'}) {
    return {id,  width: bookWidthInMm, height: bookHeightInMm, color, x: xposition, y: yposition, title};
}