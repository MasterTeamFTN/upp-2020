//package rs.ac.uns.ftn.uppservice.util;
//
//import rs.ac.uns.ftn.uppservice.model.MembershipDecision;
//
//import javax.persistence.AttributeConverter;
//import javax.persistence.Converter;
//import java.util.stream.Stream;
//
//@Converter(autoApply = true)
//public class MembershipDecisionMapper implements AttributeConverter<MembershipDecision, String> {
//
//    @Override
//    public String convertToDatabaseColumn(MembershipDecision membershipDecision) {
//        if (membershipDecision == null) {
//            return null;
//        }
//        return membershipDecision.getLabel();
//    }
//
//    @Override
//    public MembershipDecision convertToEntityAttribute(String code) {
//        if (code == null) {
//            return null;
//        }
//
//        return Stream.of(MembershipDecision.values())
//                .filter(membershipDecision -> membershipDecision.getLabel().equals(code))
//                .findFirst()
//                .orElseThrow(IllegalArgumentException::new);
//    }
//}
