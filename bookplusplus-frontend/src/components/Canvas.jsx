import React, { forwardRef, useEffect, useImperativeHandle, useRef } from 'react';
import { Stage, Layer, Rect, Text, Group } from 'react-konva';
import { createBook } from "../utils/createBook.js";

const Canvas = forwardRef(({ books }, ref) => {
    const booksRef = useRef([]);

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
            style={{ backgroundColor: '#f0f0f0' }}
        >
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
                            width={book.width}
                            height={book.height}
                            fill={book.color}
                            cornerRadius={2}
                        />
                        <Text
                            x={0}
                            y={book.height - 5}
                            text={book.title}
                            fill="white"
                            fontSize={12}
                            rotation={-90}
                            width={book.height - 10}
                            height={book.width}
                            verticalAlign={'middle'}
                        />
                    </Group>
                ))}
            </Layer>
        </Stage>
    );
});

export default Canvas;
