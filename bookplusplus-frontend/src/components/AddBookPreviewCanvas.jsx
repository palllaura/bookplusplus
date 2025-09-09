import React, {forwardRef} from 'react';
import {Stage, Layer, Rect, Text} from 'react-konva';

const Canvas = forwardRef(({title, pages, height, color}) => {
    const mm = window.innerHeight / 1000;

    const bookTitle = title ? title : 'Book Title';
    const bookWidth = pages ? pages * mm / 10 : 50 * mm;
    const bookHeight = height ? height * mm : 240 * mm;
    const bookColor = color ? color : 'black';

    return (
        <Stage
            width={bookWidth}
            height={bookHeight}
        >
            <Layer>
                <Rect
                    width={bookWidth}
                    height={bookHeight}
                    fill={bookColor}
                    cornerRadius={2}
                />
                <Text
                    x={0}
                    y={bookHeight - 5*mm}
                    text={bookTitle}
                    fill="white"
                    fontSize={16*mm}
                    rotation={-90}
                    width={bookHeight - 10*mm}
                    height={bookWidth}
                    verticalAlign={'middle'}
                />
            </Layer>

        </Stage>
    );
});

export default Canvas;
