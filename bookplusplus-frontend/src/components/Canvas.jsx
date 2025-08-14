import React, {forwardRef, useEffect, useImperativeHandle, useRef, useState} from 'react';
import { Stage, Layer, Rect, Text, Group } from 'react-konva';
import { createBook } from "../utils/createBook.js";

const Canvas = forwardRef(({ books }, ref) => {
    const [drawnBooks, setDrawnBooks] = useState([]);
    const mm = window.innerHeight / 1000;

    useImperativeHandle(ref, () => ({
        getCurrentBookLocations: () => {
            return drawnBooks.map(book => ({
                id: book.id,
                xposition: book.x / mm,
                yposition: book.y / mm
            }));
        },
        reloadBooks: (newBooks) => {
            setDrawnBooks(newBooks.map(book => createBook(book)));
        }
    }));

    useEffect(() => {
        setDrawnBooks(books.map(book => createBook(book)));
    }, [books]);

    const handleDragMove = (e, id) => {
        const { x, y } = e.target.position();
        setDrawnBooks(prev =>
            prev.map(book =>
                book.id === id ? { ...book, x: x / mm, y: y / mm } : book
            )
        );
    };

    return (
        <Stage
            width={window.innerWidth}
            height={window.innerHeight}
            style={{ backgroundColor: '#F7E1CA' }}
        >
            <Layer>
                <Rect
                    x={100 * mm}
                    y={10 * mm}
                    height={900 * mm}
                    width={20 * mm}
                    fill={'#88664B'}
                />
                <Rect
                    x={100 * mm + 800 * mm - 20 * mm}
                    y={10 * mm}
                    height={900 * mm}
                    width={20 * mm}
                    fill={'#88664B'}
                />
                <Rect
                    x={100 * mm}
                    y={10 * mm}
                    height={20 * mm}
                    width={800 * mm}
                    fill={'#88664B'}
                />
                <Rect
                    x={100 * mm}
                    y={10 * mm + 300 * mm}
                    height={20 * mm}
                    width={800 * mm}
                    fill={'#88664B'}
                />
                <Rect
                    x={100 * mm}
                    y={10 * mm + 600 * mm}
                    height={20 * mm}
                    width={800 * mm}
                    fill={'#88664B'}
                />
                <Rect
                    x={100 * mm}
                    y={10 * mm + 900 * mm}
                    height={40 * mm}
                    width={800 * mm}
                    fill={'#88664B'}
                />
            </Layer>
            <Layer>
                {drawnBooks.map(book => (
                    <Group
                        key={book.id}
                        x={book.x * mm}
                        y={book.y * mm}
                        draggable
                        onDragMove={(e) => handleDragMove(e, book.id)}
                    >
                        <Rect
                            width={book.width * mm}
                            height={book.height * mm}
                            fill={book.color}
                            cornerRadius={2}
                        />
                        <Text
                            x={0}
                            y={book.height * mm - 5 * mm}
                            text={book.title}
                            fill="white"
                            fontSize={16 * mm}
                            rotation={-90}
                            width={book.height * mm - 10 * mm}
                            height={book.width * mm}
                            verticalAlign={'middle'}
                        />
                    </Group>
                ))}
            </Layer>
        </Stage>
    );
});

export default Canvas;
