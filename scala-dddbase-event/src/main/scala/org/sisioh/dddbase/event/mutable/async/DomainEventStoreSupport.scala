package org.sisioh.dddbase.event.mutable.async

import org.sisioh.dddbase.core.lifecycle.Repository
import org.sisioh.dddbase.core.model.Identity
import org.sisioh.dddbase.event.{DomainEventStore, DomainEvent}
import scala.concurrent.{Future, ExecutionContext}

/**
 * [[org.sisioh.dddbase.event.DomainEventStore]]のための骨格実装。
 *
 * @tparam R リポジトリの型
 * @tparam ID エンティティの識別子の型
 * @tparam T エンティティの型
 */
trait DomainEventStoreSupport
[+R <: Repository[R, ID, T, Future],
ID <: Identity[_],
T <: DomainEvent[ID]]
  extends DomainEventStore[R, ID, T, Future, Unit] {

  implicit val executor: ExecutionContext

  protected val eventRepository: R

  def handleEvent(event: T): Future[Unit] =
    eventRepository.store(event).map(_ => ())

}