import '../App.css';
import { useRef, useEffect } from 'react';

const Canvas = () => {

    const book = {
        width: 60,
        height: 120,
        color: '#D9722C',
        x: 100,
        y: 100,
        title: 'Book 1'
    };

    const canvasRef = useRef(null);

    const draw = (ctx, width, height) => {
        ctx.clearRect(0, 0, width, height);

        const bookWidth = book.width;
        const bookHeight = book.height;
        const x = book.x;
        const y = book.y;

        ctx.fillStyle = book.color;
        ctx.fillRect(x, y, bookWidth, bookHeight);

        ctx.fillStyle = 'white';
        ctx.font = '16px sans-serif';
        ctx.fillText(book.title, x + 2, y + bookHeight / 2);
    };

    useEffect(() => {
        const canvas = canvasRef.current;
        const parent = canvas.parentElement;

        const resizeCanvas = () => {
            canvas.width = parent.clientWidth;
            canvas.height = parent.clientHeight;

            const ctx = canvas.getContext('2d');
            draw(ctx, canvas.width, canvas.height);
        };

        resizeCanvas();

        window.addEventListener('resize', resizeCanvas);
        return () => window.removeEventListener('resize', resizeCanvas);
    }, []);

    return <canvas className="canvas" ref={canvasRef} />;
};

export default Canvas;
