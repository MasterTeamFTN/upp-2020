import { BoardMemberDto } from "./BoardMemberDto";

export interface BoardMemberDecisionDto {
    id: string;
    boardMember: BoardMemberDto;
    isPlagiarized: boolean;
}
