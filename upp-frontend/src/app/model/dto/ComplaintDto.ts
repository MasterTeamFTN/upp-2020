import { CompliantAssignmentDto } from "./CompliantAssignmentDto";
import { BoardMemberDecisionDto } from "./BoardMemberDecisionDto";

export interface ComplaintDto{
    id: string;
    plagiarisedBook: string;
    originalBook: string;
    boardMemberDecisionDtos: BoardMemberDecisionDto[];
    compliantAssignmentDtos: CompliantAssignmentDto[];
    processInstanceId: string;
    
    jurisdiction: string;
}
