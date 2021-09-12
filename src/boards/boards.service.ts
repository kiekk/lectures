import { Injectable } from '@nestjs/common';
import { Board } from './board.model';
import { BoardStatus } from '../enum/boardStatus';
import { v1 as uuid } from 'uuid';
import { CreateBoardDto } from '../dto/create-board.dto';

@Injectable()
export class BoardsService {
  private boards: Board[] = [];

  // 타입 추론이 되기 때문에 타입을 지정하지 않아도 Board[]로 인식
  getAllBoards() /*: Board[] */ {
    return this.boards;
  }

  createBoard(createBoardDto: CreateBoardDto) {
    const { title, description } = createBoardDto;
    const board: Board = {
      id: uuid(),
      title,
      description,
      status: BoardStatus.PUBLIC,
    };

    this.boards.push(board);

    return board;
  }

  getBoardById(id: string) {
    return this.boards.find((board) => board.id === id);
  }
}
