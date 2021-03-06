package awscala.iam

import com.amazonaws.services.{ identitymanagement => aws }
import java.util.Date

object AccessKey {

  def apply(ak: aws.model.AccessKey): AccessKey = new AccessKey(
    userName = ak.getUserName,
    accessKeyId = ak.getAccessKeyId,
    secretAccessKey = Option(ak.getSecretAccessKey),
    status = ak.getStatus,
    createDate = ak.getCreateDate)
  def apply(m: aws.model.AccessKeyMetadata): AccessKey = new AccessKey(
    userName = m.getUserName,
    accessKeyId = m.getAccessKeyId,
    secretAccessKey = None,
    status = m.getStatus,
    createDate = m.getCreateDate)
}

case class AccessKey(userName: String, accessKeyId: String, secretAccessKey: Option[String], status: String, createDate: Date)
  extends aws.model.AccessKey(userName, accessKeyId, status, secretAccessKey.orNull[String]) {

  def activate()(implicit iam: IAM) = iam.activateAccessKey(this)
  def inactivate()(implicit iam: IAM) = iam.inactivateAccessKey(this)
  def destroy()(implicit iam: IAM) = iam.delete(this)
}
