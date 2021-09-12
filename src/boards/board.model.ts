import { BoardStatus } from '../enum/boardStatus';

export interface Board {
  id: string;
  title: string;
  description: string;
  status: BoardStatus;
}
