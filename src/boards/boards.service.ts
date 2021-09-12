import { Injectable } from '@nestjs/common';
import { Board } from './board.model';

@Injectable()
export class BoardsService {
  private boards: Board[] = [];

  // 타입 추론이 되기 때문에 타입을 지정하지 않아도 Board[]로 인식
  getAllBoards() /*: Board[] */ {
    return this.boards;
  }
}
