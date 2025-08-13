import React, { forwardRef, useEffect, useImperativeHandle, useRef } from 'react';
import { Stage, Layer, Rect, Text, Group } from 'react-konva';
import { createBook } from "../utils/createBook.js";

const Canvas = forwardRef(({ books }, ref) => {
    const booksRef = useRef([]);
    const mm = window.innerHeight / 1000;

    useImperativeHandle(ref, () => ({
        getCurrentBookLocations: () => {
            return booksRef.current.map(book => ({
                id: book.id,
                xposition: book.x,
                yposition: book.y
            }));
        }
    }));

    useEffect(() => {
        booksRef.current = books.map(book => createBook(book));
    }, [books]);

    const handleDragMove = (e, id) => {
        const { x, y } = e.target.position();
        booksRef.current = booksRef.current.map(book =>
            book.id === id ? { ...book, x, y } : book
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
                    x={100}
                    y={10}
                    height={900 * mm}
                    width={20 * mm}
                    fill={'#88664B'}
                />
                <Rect
                    x={100 + 800 * mm - 20 * mm}
                    y={10}
                    height={900 * mm}
                    width={20 * mm}
                    fill={'#88664B'}
                />
                <Rect
                    x={100}
                    y={10}
                    height={20 * mm}
                    width={800 * mm}
                    fill={'#88664B'}
                />
                <Rect
                    x={100}
                    y={10 + 300 * mm}
                    height={20 * mm}
                    width={800 * mm}
                    fill={'#88664B'}
                />
                <Rect
                    x={100}
                    y={10 + 600 * mm}
                    height={20 * mm}
                    width={800 * mm}
                    fill={'#88664B'}
                />
                <Rect
                    x={100}
                    y={10 + 900 * mm}
                    height={40 * mm}
                    width={800 * mm}
                    fill={'#88664B'}
                />
            </Layer>
            <Layer>
                {booksRef.current.map(book => (
                    <Group
                        key={book.id}
                        x={book.x}
                        y={book.y}
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
                            y={book.height * mm - 5}
                            text={book.title}
                            fill="white"
                            fontSize={12}
                            rotation={-90}
                            width={book.height * mm - 10}
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
