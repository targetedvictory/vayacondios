require 'open-uri'
require 'nibbler'

class HadoopAttemptScraper < Nibbler
  def self.scrape_task(task_id)
    url    = "http://localhost:50030/taskdetails.jsp?tipid=#{task_id}"
    scrape = parse(open(url))
    scrape.task_id = task_id

    scrape
  end

  attr_accessor :task_id
  elements 'table.jobtasks tbody > tr' => :attempts do
    element 'td:nth-child(1)' => 'attempt_id'
    element 'td:nth-child(2) a/@href' => 'machine'
    element 'td:nth-child(3)' => 'status'
    element 'td:nth-child(4)' => 'progress'
    element 'td:nth-child(5)' => 'start_time'
    element 'td:nth-child(6)' => 'finish_time'
    element 'td:nth-child(7)' => 'errors'
  end

  def to_attempts
    attempts.map do |attempt|
      {
        _id:         attempt.attempt_id.to_s,
        task_id:     task_id,
        host:        attempt.machine.to_s.gsub(/^http:\/\//, '').gsub(/:[0-9]+$/, ''),
        status:      attempt.status,
        progress:    attempt.progress.to_f / 100.0,
        start_time:  Time.parse(attempt.start_time),
        finish_time: Time.parse(attempt.finish_time),
        errors:      attempt.errors
      }
    end
  end
end